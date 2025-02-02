

const ApiVeiculo = {
	divFiltros: () => document.getElementById("div_filtros"),
	formFiltros: () => ApiVeiculo.divFiltros().querySelector("form"),
	divTabela: () => document.getElementById("div_tabela"),
	divModal: () => DomBuilder.modal("modal-veiculo", "Cadastrar veículo"),

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

	abrirModal: (veiculo = undefined) => {
		const $divModal = ApiVeiculo.divModal();

		API.get("filtros", (status, json) => {
			if (status != 200) {
				alert("Não foi possível carregar os filtros");
				return;
			}

			renderModal($divModal, json, veiculo);
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

	onEditar: (e) => {
		e.preventDefault();
		const $tr = e.currentTarget.closest("tr");
		const codigo = $tr.getAttribute("data-vco_id");

		API.get(`veiculo/${codigo}`, function(status, veiculo) {
			if (status != 200) {
				alert("Não foi possível editar o veículo");
				return;
			}


			ApiVeiculo.abrirModal(veiculo);
		})
	},

	onExcluir: (e) => {
		e.preventDefault();
		console.log("onExcluir");
	}
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
	DomBuilder.addActionButton($table, "btn_editar", "Editar", ApiVeiculo.onEditar);
	DomBuilder.addActionButton($table, "btn_excluir", "Excluir", ApiVeiculo.onExcluir);

	const $aTr = $table.querySelectorAll("tbody > tr");
	Array.from($aTr).forEach($tr => {
		const codigo = $tr.querySelector("td:first-child").innerHTML;
		$tr.setAttribute("data-vco_id", codigo);
	});
	
}

function renderModal($divModal, filtros, veiculo = undefined) {
	const modal = new bootstrap.Modal($divModal);
	modal.toggle();

	const $modalBody = $divModal.querySelector(".modal-body");
	const $modalFooter = $divModal.querySelector(".modal-footer");

	const toggleTipoCarro = (tipoId, veiculo = undefined) => {
		const aCampos = Array.from($modalBody.querySelectorAll(".campos_carro, .campos_moto"));
		aCampos.forEach($campo => $campo.parentNode.remove());

		if (tipoId == ENV.tipo_veiculo.CARRO) {
			seletorRemove = "carros_moto";

			const iCombustivel = veiculo == undefined ? undefined : veiculo.vco_combustivel_cod;
			const $selCombustivel = DomBuilder.select("vco_combustivel", filtros.combustivel, iCombustivel);
			DomBuilder.addClass($selCombustivel, "campos_carro required")
			$modalBody.appendChild(DomBuilder.label("Tipo de combustivel", $selCombustivel));

			const iPortas = veiculo == undefined ? undefined : veiculo.vco_portas;
			const $iptPortas = DomBuilder.inputText("vco_portas", "Portas", iPortas);
			DomBuilder.addClass($iptPortas, "campos_carro required")
			$modalBody.appendChild(DomBuilder.label("Portas", $iptPortas));
		} else if (tipoId == ENV.tipo_veiculo.MOTO) {
			seletorRemove = "campos_carro";

			const iCilindradas = veiculo == undefined ? undefined : veiculo.vco_cilindradas;
			const $iptPortas = DomBuilder.inputText("vco_cilindradas", "Cilindradas", iCilindradas.replace("cc", ""));
			DomBuilder.addClass($iptPortas, "campos_moto required")
			$modalBody.appendChild(DomBuilder.label("Cilindradas", $iptPortas));
		}
	}

	const iAno = veiculo == undefined ? undefined : veiculo.vco_ano;
	const $iptAno = DomBuilder.inputText("vco_ano", "Ano de fabricação", iAno);
	$modalBody.appendChild(DomBuilder.label("Ano de fabricação", $iptAno));

	const iTipo = veiculo == undefined ? undefined : veiculo.vco_tipo_cod;
	const $selTipo = DomBuilder.select("vco_tipo", filtros.tipos, iTipo, (e) => {
		const tipoId = $selTipo.options[$selTipo.selectedIndex].value;
		toggleTipoCarro(tipoId);
	});
	$modalBody.appendChild(DomBuilder.label("Tipo do veículo", $selTipo));

	const idFabricante = veiculo == undefined ? undefined : veiculo.vco_fabricante.fbe_id;
	const $selFabricante = DomBuilder.select("fbe_id", filtros.fabricantes, idFabricante, (e) => {
		$selModelo.innerHTML = "";

		const $currentOption = $selFabricante.options[$selFabricante.selectedIndex];
		const $newSelect = DomBuilder.select("mdo_id", filtros.fabricante_modelo[$currentOption.value]);

		Array.from($newSelect.options).forEach($op => $selModelo.appendChild($op));
		$selModelo.selectedIndex = 0;
	});
	$modalBody.appendChild(DomBuilder.label("Fabricante", $selFabricante));

	const idModelo = veiculo == undefined ? undefined : veiculo.vco_modelo.mdo_id;
	const aModelo = idFabricante == undefined ? [] : filtros.fabricante_modelo[idFabricante];
	const $selModelo = DomBuilder.select("mdo_id", aModelo, idModelo);
	$modalBody.appendChild(DomBuilder.label("Modelo", $selModelo));
	
	if (iTipo != undefined) {
		toggleTipoCarro(iTipo, veiculo);
	}

	const $btnCancelar = DomBuilder.button("btn-cancelar", "Cancelar", (e) => {
		e.preventDefault();
		modal.toggle();
	});
	$modalFooter.appendChild(DomBuilder.addClass($btnCancelar, "btn btn-danger"));
	
	const $btnSalvar = DomBuilder.button("btn-salvar", "Salvar", (e) => {
		e.preventDefault();
	});
	$modalFooter.appendChild(DomBuilder.addClass($btnSalvar, "btn btn-success"));
}