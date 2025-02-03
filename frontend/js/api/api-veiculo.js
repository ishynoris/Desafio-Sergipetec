

const ApiVeiculo = {
	divFiltros: () => document.getElementById("div_filtros"),
	formFiltros: () => ApiVeiculo.divFiltros().querySelector("form"),
	divTabela: () => document.getElementById("div_tabela"),
	divModal: (titulo = "") => {
		const $modal = document.querySelector("#modal-veiculo");
		if ($modal != null) {
			$modal.remove();
		}
		return DomBuilder.modal("modal-veiculo", titulo)
	},

	index: () => {
		API.get("veiculo", function(status, json) {
			if (status != 200) {
				alert("Erro ao consultar os veículos");
				return;
			}

			renderFiltros(ApiVeiculo.formFiltros(), json.filtros);
			renderTabela(ApiVeiculo.divTabela(), json.veiculos);	
		})
	},

	reload: () => {
		ApiVeiculo.formFiltros().querySelector("div.row").remove();
		ApiVeiculo.divTabela().querySelector("table").remove();
		ApiVeiculo.index();
	},

	abrirModal: (veiculo = undefined) => {
		const titulo = veiculo == undefined ? "Cadastrar veículo" : "Atualizar dados";
		const $divModal = ApiVeiculo.divModal(titulo);

		API.get("filtros", (status, json) => {
			if (status != 200) {
				alert("Não foi possível carregar os filtros");
				return;
			}

			renderModal($divModal, json, veiculo);
		})
	},

	cadastrar: () => {

		const callback = (status, json) => {
			if (!json.success) {
				alert(`Não foi possível cadastrar o veículo: ${json.message}`);
				return;
			}
			ApiVeiculo.reload();
		}

		const data = new FormData(document.getElementById("frm-veiculo"));
		const id = data.get("vco_id");
		id == null ? API.post("veiculo", data, callback)
				: API.put(`veiculo/${id}`, data, callback); 
	},

	apagar: (codigo) => {
		API.delete("veiculo", codigo, (status, json) => {
			if (!json.success) {
				alert(`Não foi possível apagar o veículo: ${json.message}`);
				return;
			};
			debugger;
			ApiVeiculo.reload();
		});
	},

	consultar: (codigo) => {
		API.get(`veiculo/${codigo}`, function(status, veiculo) {
			if (status != 200) {
				alert("Não foi possível editar o veículo");
				return;
			}


			ApiVeiculo.abrirModal(veiculo);
		})
	},

	onFiltrar: (e) => {
		e.preventDefault();
		const formData =  new FormData(ApiVeiculo.formFiltros());
		const params = new URLSearchParams(formData).toString();
		API.get(`veiculo/pesquisar?${params}`, function (status, json) {
			if (status != 200) {
				return;
			}

			renderTabela(ApiVeiculo.divTabela(), json, true);
		})
	},
}

function renderFiltros($form, filtros) {
	const appendDiv = ($el) => {
		const $div = DomBuilder.div("col");
		$div.appendChild($el);
		return $div;
	}

	const tipos = filtros.tipos;
	const modelos = filtros.modelos;

	const $div = DomBuilder.div("row");
	$div.appendChild(appendDiv(DomBuilder.inputText("vco_ano", "Ano")));
	$div.appendChild(appendDiv(DomBuilder.select("vco_tipo", tipos)));
	$div.appendChild(appendDiv(DomBuilder.select("mdo_id", modelos)));
	$div.appendChild(appendDiv(DomBuilder.button("btn_filtrar", "Filtrar", ApiVeiculo.onFiltrar)));
	$div.appendChild(appendDiv(DomBuilder.button("btn_cadastrar", "Cadastrar", (e) => {
		e.preventDefault();
		ApiVeiculo.abrirModal();
	})));

	$form.method = "post";
	$form.appendChild($div);

	DomBuilder.addClass(document.querySelector("button[name='btn_filtrar']"), "btn btn-secondary");
	DomBuilder.addClass(document.querySelector("button[name='btn_cadastrar']"), "btn btn-primary");
}

function renderTabela($div, aVeiculos, clearBefore = false) {
	if (clearBefore) {
		$div.querySelector("table").remove();
	}

	const aTitulos = [ "#", "Tipo", "Ano", "Descrição", "Valor" ];
	const aMatriz = aVeiculos.map(veiculo => [
		veiculo.vco_id,
		veiculo.vco_tipo_text, 
		veiculo.vco_ano, 
		veiculo.vco_descricao, 
		veiculo.vco_preco_moeda 
	]);
	$div.appendChild(DomBuilder.table(aTitulos, aMatriz));

	const $table = $div.querySelector("table");
	DomBuilder.addActionButton($table, "btn_editar", "Editar", e => {
		e.preventDefault();
		const $tr = e.currentTarget.closest("tr");
		const codigo = $tr.getAttribute("data-vco_id");
		ApiVeiculo.consultar(codigo);
	});
	DomBuilder.addActionButton($table, "btn_excluir", "Excluir", e => {
		e.preventDefault();
		const $tr = e.currentTarget.closest("tr");
		const codigo = $tr.getAttribute("data-vco_id");
		ApiVeiculo.apagar(codigo);
	});

	const $aTr = $table.querySelectorAll("tbody > tr");
	Array.from($aTr).forEach($tr => {
		const codigo = $tr.querySelector("td:first-child").innerHTML;
		$tr.setAttribute("data-vco_id", codigo);
	});
	
}

function renderModal($divModal, filtros, veiculo = undefined) {
	const modal = new bootstrap.Modal($divModal);
	modal.toggle();

	const $form = DomBuilder.formPost("frm-veiculo");
	const $modalBody = $divModal.querySelector(".modal-body");
	const $modalFooter = $divModal.querySelector(".modal-footer");

	if (veiculo != undefined) {
		$form.appendChild(DomBuilder.inputHidden("vco_id", veiculo.vco_id));
	}

	const iAno = veiculo == undefined ? undefined : veiculo.vco_ano;
	const $iptAno = DomBuilder.inputText("vco_ano", "Ano de fabricação", iAno);
	$form.appendChild(DomBuilder.label("Ano de fabricação", $iptAno));

	const preco = veiculo == undefined ? undefined : veiculo.vco_preco;
	const $iptPreco = DomBuilder.inputText("vco_preco", "Preço", preco);
	$form.appendChild(DomBuilder.label("Preço", $iptPreco));

	const tipo = veiculo == undefined ? undefined : veiculo.vco_tipo_cod;
	const $selTipo = DomBuilder.select("vco_tipo", filtros.tipos, tipo, (e) => {
		const tipoId = $selTipo.options[$selTipo.selectedIndex].value;
		toggleTipoCarro($form, filtros, tipoId);
	});
	$form.appendChild(DomBuilder.label("Tipo do veículo", $selTipo));

	const idFabricante = veiculo == undefined ? undefined : veiculo.vco_fabricante.fbe_id;
	const $selFabricante = DomBuilder.select("fbe_id", filtros.fabricantes, idFabricante, (e) => {
		$selModelo.innerHTML = "";

		const $currentOption = $selFabricante.options[$selFabricante.selectedIndex];
		const $newSelect = DomBuilder.select("mdo_id", filtros.fabricante_modelo[$currentOption.value]);

		Array.from($newSelect.options).forEach($op => $selModelo.appendChild($op));
		$selModelo.selectedIndex = 0;
	});
	$form.appendChild(DomBuilder.label("Fabricante", $selFabricante));

	const idModelo = veiculo == undefined ? undefined : veiculo.vco_modelo.mdo_id;
	const aModelo = idFabricante == undefined ? [] : filtros.fabricante_modelo[idFabricante];
	const $selModelo = DomBuilder.select("mdo_id", aModelo, idModelo);
	$form.appendChild(DomBuilder.label("Modelo", $selModelo));
	
	if (tipo != undefined) {
		toggleTipoCarro($form, filtros, tipo, veiculo);
	}

	const $btnCancelar = DomBuilder.button("btn-cancelar", "Cancelar", (e) => {
		e.preventDefault();
		modal.toggle();
		ApiVeiculo.divModal().remove();
	});
	$modalFooter.appendChild(DomBuilder.addClass($btnCancelar, "btn btn-danger"));
	
	const $btnSalvar = DomBuilder.button("btn-salvar", "Salvar", e => {
		e.preventDefault();
		ApiVeiculo.cadastrar();
		modal.toggle();
		ApiVeiculo.divModal().remove();
	});
	$modalFooter.appendChild(DomBuilder.addClass($btnSalvar, "btn btn-success"));

	$modalBody.appendChild($form);
}

function toggleTipoCarro($form, filtros, tipoId, veiculo = undefined) {
	const aCampos = Array.from($form.querySelectorAll(".campos_carro, .campos_moto"));
	aCampos.forEach($campo => $campo.parentNode.remove());

	if (tipoId == ENV.tipo_veiculo.CARRO) {
		seletorRemove = "carros_moto";

		const iCombustivel = veiculo == undefined ? undefined : veiculo.vco_combustivel_cod;
		const $selCombustivel = DomBuilder.select("vco_combustivel", filtros.combustivel, iCombustivel);
		DomBuilder.addClass($selCombustivel, "campos_carro required")
		$form.appendChild(DomBuilder.label("Tipo de combustivel", $selCombustivel));

		const iPortas = veiculo == undefined ? undefined : veiculo.vco_portas;
		const $iptPortas = DomBuilder.inputText("vco_portas", "Portas", iPortas);
		DomBuilder.addClass($iptPortas, "campos_carro required")
		$form.appendChild(DomBuilder.label("Portas", $iptPortas));
	} else if (tipoId == ENV.tipo_veiculo.MOTO) {
		seletorRemove = "campos_carro";

		const cilindradas = veiculo == undefined ? "" : veiculo.vco_cilindradas;
		const $iptPortas = DomBuilder.inputText("vco_cilindradas", "Cilindradas", cilindradas.replace("cc", ""));
		DomBuilder.addClass($iptPortas, "campos_moto required")
		$form.appendChild(DomBuilder.label("Cilindradas", $iptPortas));
	}
}