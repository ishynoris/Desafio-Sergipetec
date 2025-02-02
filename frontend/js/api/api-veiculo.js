

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
	}
}

function renderFiltros($form, filtros) {
	const tipos = filtros.tipos;
	const modelos = filtros.modelos;

	$form.method = "post";
	$form.appendChild(DomBuilder.inputText("vco_ano", "Ano"));
	$form.appendChild(DomBuilder.select("vco_tipo", tipos));
	$form.appendChild(DomBuilder.button("btn-filtrar", "Filtrar", ApiVeiculo.onFiltrar));
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
}