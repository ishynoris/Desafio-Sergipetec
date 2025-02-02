

const ApiVeiculo = {
	divFiltros: () => document.getElementById("div_filtros"),
	formFiltros: () => ApiVeiculo.divFiltros().querySelector("form"),
	divTabela: () => document.getElementById("div_tabela"),

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

	abrirModal: () => {
		const $divModal = DomBuilder.modal("modal-veiculo", "Cadastrar veículo");

		API.get("filtros", (status, json) => {
			if (status != 200) {
				alert("Não foi possível carregar os filtros");
				return;
			}

			renderModal($divModal, json);
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
		console.log("onEditar")
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
}

function renderModal($divModal, filtros, veiculo = undefined) {
	const modal = new bootstrap.Modal($divModal);
	modal.toggle();

	const $modalBody = $divModal.querySelector(".modal-body");
	const $modalFooter = $divModal.querySelector(".modal-footer");

	const $iptAno = DomBuilder.inputText("vco_ano", "Ano de fabricação");
	$modalBody.appendChild(DomBuilder.label("Ano de fabricação", $iptAno));
	
	const $selTipo = DomBuilder.select("vco_tipo", filtros.tipos, (e) => {
		const tipoId = $selTipo.options[$selTipo.selectedIndex].value;
		const aCampos = Array.from($modalBody.querySelectorAll(".campos_carro, .campos_moto"));
		aCampos.forEach($campo => $campo.parentNode.remove());
		
		if (tipoId == ENV.tipo_veiculo.CARRO) {
			seletorRemove = "carros_moto";

			const $selCombustivel = DomBuilder.select("vco_combustivel", filtros.combustivel);
			DomBuilder.addClass($selCombustivel, "campos_carro required")
			$modalBody.appendChild(DomBuilder.label("Tipo de combustivel", $selCombustivel));

			const $iptPortas = DomBuilder.inputText("vco_portas", "Portas");
			DomBuilder.addClass($iptPortas, "campos_carro required")
			$modalBody.appendChild(DomBuilder.label("Portas", $iptPortas));
		} else if (tipoId == ENV.tipo_veiculo.MOTO) {
			seletorRemove = "campos_carro";

			const $iptPortas = DomBuilder.inputText("vco_cilindradas", "Cilindradas");
			DomBuilder.addClass($iptPortas, "campos_moto required")
			$modalBody.appendChild(DomBuilder.label("Cilindradas", $iptPortas));
		}
	});
	$modalBody.appendChild(DomBuilder.label("Tipo do veículo", $selTipo));

	const $selFabricante = DomBuilder.select("fbe_id", filtros.fabricantes, (e) => {
		$selModelo.innerHTML = "";

		const $currentOption = $selFabricante.options[$selFabricante.selectedIndex];
		const $newSelect = DomBuilder.select("mdo_id", filtros.fabricante_modelo[$currentOption.value]);

		Array.from($newSelect.options).forEach($op => $selModelo.appendChild($op));
		$selModelo.selectedIndex = 0;
	});
	$modalBody.appendChild(DomBuilder.label("Fabricante", $selFabricante));

	const $selModelo = DomBuilder.select("mdo_id", [ ]);
	$modalBody.appendChild(DomBuilder.label("Modelo", $selModelo));

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