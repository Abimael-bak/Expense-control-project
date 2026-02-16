const API_URL = "http://localhost:8080/expenses";

let expenseIdToUpdate = null;

// ----------------------
//  CARREGAMENTO INICIAL
// ----------------------
document.addEventListener("DOMContentLoaded", () => {
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    if (id) {
        loadExpenseById(id);
    }

    // Só executa na página principal
    if (document.getElementById("expense-list")) {
        renderUser();
    }
});

// ----------------------
//  FUNÇÃO CENTRALIZADA
//  BUSCA TODAS AS DESPESAS DO USUÁRIO
// ----------------------
function loadUserExpenses() {
    const user = JSON.parse(localStorage.getItem("user"));
    if (!user) return;

    fetch(`http://localhost:8080/users/${user.id}/expenses`)
        .then(response => response.json())
        .then(expenses => {
            renderExpense(expenses);
        })
        .catch(error => console.error("Erro ao carregar despesas:", error));
}


// ----------------------
//  CARREGA O USUÁRIO E AS DESPESAS
// ----------------------
function renderUser() {
    const user = JSON.parse(localStorage.getItem("user"));

    if (!user) {
        window.location.href = "login.html";
        return;
    }

    // Agora sempre busca do backend
    loadUserExpenses();
}

// ----------------------
//  RENDERIZAÇÂO DA TABELA
// ----------------------
function renderExpense(expenses) {
    const tableBody = document.getElementById("expense-list");
    const totalSpan = document.getElementById("total");

    if (!tableBody || !totalSpan) return;

    tableBody.innerHTML = "";
    let total = 0;

    expenses.forEach(e => {
        const date = e.moment.split("T")[0];

        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td>${e.id}</td>
            <td>${e.description}</td>
            <td>${e.amount.toFixed(2)}</td>
            <td>${e.category.name}</td>
            <td>${date}</td>
            <td>
                <button onclick="Update(${e.id})">Atualizar</button>
                <button onclick="Delete(${e.id})">Deletar</button>
            </td>
        `;
        tableBody.appendChild(tr);

        total += e.amount;
    });

    totalSpan.textContent = total.toFixed(2);
}

// ----------------------
//  CARREGA UMA DESPESA PARA EDIÇÃO
// ----------------------
function loadExpenseById(id) {
    fetch(`${API_URL}/${id}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById("description").value = data.description;
            document.getElementById("amount").value = data.amount;
            document.getElementById("category").value = data.category.name;

            expenseIdToUpdate = id;
        })
        .catch(error => console.error("Erro carregando despesa:", error));
}

// ----------------------
//  REDIRECIONA PARA PÁGINA DE ATUALIZAÇÃO
// ----------------------
function Update(id) {
    window.location.href = `insert.html?id=${id}`;
}

// ----------------------
//  INSERIR OU ATUALIZAR DESPESA
// ----------------------
function addExpense() {
    const description = sanitize(document.getElementById("description").value.trim());
    const amount = document.getElementById("amount").value;
    const category = sanitize(document.getElementById("category").value.trim());
    const user = JSON.parse(localStorage.getItem("user"));

    if (!description || !amount || !category) {
        alert("Por favor, preencha todos os campos.");
        return;
    }

    const method = expenseIdToUpdate ? "PUT" : "POST";
    const url = expenseIdToUpdate ? `${API_URL}/${expenseIdToUpdate}` : API_URL;

    const expense = {
        description: description,
        amount: Number(amount),
        moment: new Date().toISOString(),
        category: { name: category },
        user: { name: user.name }   // corrigido!
    };

    fetch(url, {
        method: method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(expense)
    })
        .then(response => {
            if (!response.ok) throw new Error("Erro ao salvar despesa.");
            return response.json();
        })
        .then(() => {
            loadUserExpenses();
            clearForm();

            window.location.href = "index.html";
            expenseIdToUpdate = null;
        })
        .catch(error => {
            alert("Erro ao salvar despesa: " + error.message);
            console.error(error);
        });
}

// ----------------------
//  DELETAR DESPESA
// ----------------------
function Delete(id) {
    fetch(`${API_URL}/${id}`, { method: "DELETE" })
        .then(response => {
            if (!response.ok) throw new Error("Erro ao deletar despesa.");
        })
        .then(loadUserExpenses)
        .catch(error => alert("Erro ao deletar despesa: " + error.message));
}

// ----------------------
//  BUSCA
// ----------------------
function searchExpenses() {
    const inputValue = (document.getElementById("search-input").value.toLowerCase().trim());
    const user = JSON.parse(localStorage.getItem("user"));

    if (!inputValue) {
        loadUserExpenses();
        return;
    }

    // ---- Busca por valor (>100)
    if (inputValue.startsWith(">")) {
        const amount = inputValue.substring(1).trim();

        fetch(`http://localhost:8080/users/${user.id}/expenses`)
            .then(response => response.json())
            .then(data => {
                const filtered = data.filter(e => e.amount > Number(amount));
                renderExpense(filtered);
            });
        return;
    }

    // ---- Busca por categoria (category/2)
    if (/^category\/\d+$/.test(inputValue)) {
        const categoryId = inputValue.split("/")[1];

        fetch(`http://localhost:8080/users/${user.id}/expenses`)
            .then(response => response.json())
            .then(data => {
                const filtered = data.filter(e => e.category.id === Number(categoryId));
                renderExpense(filtered);
            });
        return;
    }

    // ---- Busca por ID
    if (!isNaN(inputValue)) {
        fetch(`http://localhost:8080/users/${user.id}/expenses`)
            .then(response => response.json())
            .then(data => {
                data.forEach(e => {
                    if (e.id === Number(inputValue)) {
                        renderExpense([e]);
                    }
            });
        });
        return;
    }

    // ---- Busca geral
    fetch(`http://localhost:8080/users/${user.id}/expenses`)
        .then(response => response.json())
        .then(data => {
            const filtered = data.filter(e =>
                (e.description.toLowerCase().includes(inputValue) ||
                    e.category.name.toLowerCase().includes(inputValue))
            );
            renderExpense(filtered);
        });
}

// ----------------------
//  LIMPAR FORMULÁRIO
// ----------------------
function clearForm() {
    document.getElementById("description").value = "";
    document.getElementById("amount").value = "";
    document.getElementById("category").value = "";
}

function sanitize(input) {
    const div = document.createElement("div");
    div.textContent = input;
    return div.innerHTML;
}

function logout() {
    localStorage.removeItem("user");
    window.location.replace("login.html"); 
}
