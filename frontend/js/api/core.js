const ENV = {
	api_url: "http://localhost:8080"
}

const API = {
	get: (resource, callback) => {
		const request = new XMLHttpRequest;
		request.open("GET", `${ENV.api_url}/${resource}`);
		request.onreadystatechange = (e) => {
			if (request.readyState == 4) {
				const json = JSON.parse(request.responseText);
				callback(request.status, json);
			}
		};
		request.send(null);
	}
}

const DomBuilder = {

	div: (classList) => {
		const $div = document.createElement("div");
		const aClass = classList.split(" ");
		for (const index in aClass) {
			$div.classList.add(aClass[index]);
		}
		return $div;
	},
	
	inputText: (name, placeholder = "") => {
		const $input = document.createElement("input");
		$input.name = name;
		$input.type = "text";
		$input.placeholder = placeholder;
		return $input;
	},

	cell: (texto, cell = "td") => {
		const $cell = document.createElement(cell);
		const $text = document.createTextNode(texto);
		$cell.appendChild($text)
		return $cell;
	},

	tr: (aTextos, cell) => {
		const $tr = document.createElement("tr");
		for (const index in aTextos) {
			const titulo = aTextos[index];
			$tr.appendChild(DomBuilder.cell(titulo, cell));
		}
		return $tr;
	},

	thead: (aTitulos) => {
		const $thead = document.createElement("thead");
		$thead.appendChild(DomBuilder.tr(aTitulos, "th"));
		return $thead;
	},

	tbody: (aMatriz) => {
		const $tbody = document.createElement("tbody");
		for (const iMatriz in aMatriz) {
			const aTextos = aMatriz[iMatriz];
			$tbody.appendChild(DomBuilder.tr(aTextos, "td"));
		}
		return $tbody;
	},

	table: (aTitulos, aMatriz) => {
		const $table = document.createElement("table");
		$table.classList.add("table", "table-striped");

		$table.appendChild(DomBuilder.thead(aTitulos));
		$table.appendChild(DomBuilder.tbody(aMatriz));
		return $table;
	},

	select: (name, options) => {
		const $select = document.createElement("select");
		$select.name = name;
		$select.classList.add("form-select");

		$select.appendChild(DomBuilder.option("", "Selecionar todos"));
		for (const codigo in options) {
			const option = options[codigo];
			$select.appendChild(DomBuilder.option(codigo, option));
		}
		return $select;
	},

	option: (codigo, texto) => {		
		const $option = document.createElement("option");
		$option.value = codigo;
		$option.innerHTML = texto;
		return $option;
	},

	button: (name, texto, onClick) => {
		const $button = document.createElement("button");
		$button.name = name;
		$button.innerHTML = texto;
		$button.addEventListener("click", onClick);
		$button.classList.add("btn");
		return $button;
	},

	addActionButton: ($table, name, texto, onClick) => {
		let $thAcoes = $table.querySelector("th.acoes");
		if ($thAcoes == undefined || $thAcoes == null) {
			const $trTitulos = $table.querySelector("thead > tr");
			const $thAcoes = DomBuilder.cell("Acoes", "th");
			$thAcoes.classList.add("acoes");

			$trTitulos.appendChild($thAcoes);
		}

		const $tdAcoes = $table.querySelectorAll("td.acoes");
		let $aTdAcoes = $tdAcoes == null || $tdAcoes == undefined ? [] : Array.from($tdAcoes);
		if ($aTdAcoes.length == 0) {
			const $aTr = Array.from($table.querySelectorAll("tbody > tr"));
			for (const index in $aTr) {
				const $cell = DomBuilder.cell("", "td");
				$cell.classList.add("acoes");

				$aTr[index].appendChild($cell);
				$aTdAcoes.push($cell);
			}
		}

		for (const index in $aTdAcoes) {
			const $btnAcao = DomBuilder.button(`${name}_#${index}`, texto, onClick);
			$btnAcao.classList.add(name, "btn", "btn-outline-primary");
			$aTdAcoes[index].appendChild($btnAcao);
		}
	}
}