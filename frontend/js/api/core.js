const ENV = {
	api_url: "http://localhost:8080",
	tipo_veiculo: {
		CARRO: 0,
		MOTO: 1,
	},
	tipo_combustivel: {
		GASOLINA: 0, 
		ETANOL: 1, 
		DIESEL: 2, 
		FLEX: 3
	}
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
		return DomBuilder.addClass($div, classList);
	},
	
	label: (texto, $el) => {
		if ($el.id == "") {
			$el.id = $el.name;
		}

		const $label = document.createElement("label");
		$label.innerText = texto;
		$label.for = $el.id;

		const $div = DomBuilder.div("form-floating");
		$div.appendChild($el);
		$div.appendChild($label);
		return $div;
	},

	inputText: (name, placeholder = "", value = "") => {
		const $input = document.createElement("input");
		$input.name = name;
		$input.type = "text";
		$input.value = value;
		$input.placeholder = placeholder;
		return DomBuilder.addClass($input, "form-control");
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

	select: (name, options, value = undefined, onChange = undefined) => {
		const $select = document.createElement("select");
		$select.name = name;
		$select.classList.add("form-select");

		$select.appendChild(DomBuilder.option("", "Selecionar todos"));
		let index = 0;
		for (const codigo in options) {
			const texto = options[codigo];
			const $option = DomBuilder.option(codigo, texto);
			if (value != undefined && codigo == value) {
				$option.selected = "selected";
			}

			$select.appendChild($option);
		}

		if (onChange != undefined) {
			$select.addEventListener("change", onChange);
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

	modal: (id, title) => {
		const $divModal = DomBuilder.div("modal fade");
		$divModal.id = id;

		$divModal.innerHTML = `
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
				  <h1 class="modal-title fs-5" id="modal-veiculo-label">${title}</h1>
				  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer"></div>
			</div>
		</div>
  		`
		return $divModal;
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
	},

	addClass: ($el, classes) => {
		classes.split(" ").forEach(cls => {
			$el.classList.add(cls);
		});
		return $el;
	},

	addPseudo: ($el, key, value) => {
		$el.setAttribute(`data_${key}`, value);
		return $el;
	}
}