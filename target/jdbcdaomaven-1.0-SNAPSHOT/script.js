function criaAjax(url, dados, funcao) {
    let ajax = new XMLHttpRequest();
    ajax.onreadystatechange = funcao;
    ajax.open("GET", url +"?"+ dados, true);  
    ajax.send();
}

document.getElementById("botao").onclick = (evento) => {
    evento.preventDefault();
    let nome = document.getElementById("nome").value;
    let idade = document.getElementById("idade").value;
    criaAjax("controller", "nome=" + nome + "&idade=" + idade, function () {
        if (this.readyState == 4 && this.status == 200) {
            alert(this.responseText);
            criaAjax("listar", "", mostrar);
        }
    })
}

function mostrar() {
    if (this.readyState == 4 && this.status == 200) {
        let raiz = this.responseXML.documentElement;
        let pessoas = raiz.getElementsByTagName("pessoa");
        let txt = "<table class='table table-striped'><thead class='table-dark'><tr><th>Código</th><th>Nome</th><th>Idade</th></tr></thead>";
        for (let pessoa of pessoas) {
            let codigo = pessoa.getElementsByTagName("codigo")[0].firstChild.nodeValue;
            let nome = pessoa.getElementsByTagName("nome")[0].firstChild.nodeValue;
            let idade = pessoa.getElementsByTagName("idade")[0].firstChild.nodeValue;
            txt += `<tr><td>${codigo}</td><td>${nome}</td><td>${idade}</td></tr>`
        }
        txt += "</table>";
        document.getElementById("lista").innerHTML = txt;
        nome = document.getElementById("nome").value = "";
        idade = document.getElementById("idade").value = "";
    }
}

onload=()=>{criaAjax("listar", "", mostrar);};