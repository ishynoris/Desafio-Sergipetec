

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


	onFiltrar: (e) => {
		e.preventDefault();
		const formData =  new FormData(ApiVeiculo.formFiltros());
		const params = new URLSearchParams(formData).toString();
		API.get(`veiculo/filtro?${params}`, function (status, json) {
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
	$div.appendChild(appendDiv(DomBuilder.button("btn-filtrar", "Filtrar", ApiVeiculo.onFiltrar)));

	$form.method = "post";
	$form.appendChild($div);
}

function renderTabela($div, aVeiculos, clearBefore = false) {
	if (clearBefore) {
		$div.querySelector("table").remove();
	}

	const aTitulos = [ "Tipo", "Ano", "Descrição", "Valor" ];
	const aMatriz = aVeiculos.map(veiculo => [ 
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