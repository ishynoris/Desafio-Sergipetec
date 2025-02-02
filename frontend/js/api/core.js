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
		$table.appendChild(DomBuilder.thead(aTitulos));
		$table.appendChild(DomBuilder.tbody(aMatriz));
		return $table;
	},

	select: (name, options) => {
		const $select = document.createElement("select");
		$select.name = name;

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
		return $button;
	}
}