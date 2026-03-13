const API_URL = "http://localhost:8080";
let expenseIdToUpdate = null;

// ----------------------
// TOKEN E USER
// ----------------------

function getToken() {
    return localStorage.getItem("token");
}

function getUserId() {

    const token = getToken();

    if (!token) return null;

    if (typeof jwt_decode === "undefined") return null;

    const decoded = jwt_decode(token);

    return decoded.sub;
}

// ----------------------
// CARREGAMENTO INICIAL
// ----------------------

document.addEventListener("DOMContentLoaded", () => {

    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    if (id) {
        loadExpenseById(id);
    }

    if (document.getElementById("expense-list")) {
        renderUser();
    }

});

// ----------------------
// BUSCA DESPESAS DO USUÁRIO
// ----------------------

function loadUserExpenses() {

    const token = getToken();
    const userId = getUserId();

    if (!token || !userId) return;

    fetch(`${API_URL}/users/expenses`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    })
        .then(res => res.json())
        .then(renderExpense)
        .catch(error => console.error("Erro ao carregar despesas:", error));
}

// ----------------------
// RENDER USER
// ----------------------

function renderUser() {

    const token = getToken();

    if (!token) {
        window.location.href = "login.html";
        return;
    }

    loadUserExpenses();
}

// ----------------------
// RENDER TABELA
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
// CARREGAR DESPESA POR ID
// ----------------------

function loadExpenseById(id) {

    const token = getToken();

    fetch(`${API_URL}/expenses/${id}`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    })
        .then(res => res.json())
        .then(data => {

            document.getElementById("description").value = data.description;
            document.getElementById("amount").value = data.amount;
            document.getElementById("category").value = data.category.name;

            expenseIdToUpdate = id;

        })
        .catch(error => console.error("Erro carregando despesa:", error));
}

// ----------------------
// REDIRECIONA PARA UPDATE
// ----------------------

function Update(id) {
    window.location.href = `insert.html?id=${id}`;
}

// ----------------------
// CRIAR OU ATUALIZAR DESPESA
// ----------------------

function addExpense() {

    const description = sanitize(document.getElementById("description").value.trim());
    const amount = document.getElementById("amount").value;
    const category = sanitize(document.getElementById("category").value.trim());

    const token = getToken();

    if (!description || !amount || !category) {
        alert("Preencha todos os campos.");
        return;
    }

    const method = expenseIdToUpdate ? "PUT" : "POST";

    const url = expenseIdToUpdate
        ? `${API_URL}/expenses/${expenseIdToUpdate}/update`
        : `${API_URL}/expenses`;

    const expense = {
        description,
        amount: Number(amount),
        moment: new Date().toISOString(),
        category: { name: category }
        
    };

    fetch(url, {
        method: method,
        headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json"
        },
        body: JSON.stringify(expense)
    })
        .then(res => {

            if (!res.ok) {
                throw new Error("Erro ao salvar despesa.");
            }
            return res.json();

        })
        .then(() => {

            loadUserExpenses();
            clearForm();

            window.location.href = "index.html";

        })
        .catch(error => {

            alert("Erro ao salvar despesa.");
            console.error(error);

        });
}

// ----------------------
// DELETAR DESPESA
// ----------------------

function Delete(id) {

    const token = getToken();

    fetch(`${API_URL}/expenses/${id}`, {
        method: "DELETE",
        headers: {
            Authorization: `Bearer ${token}`
        }
    })
        .then(res => {

            if (!res.ok) {
                throw new Error("Erro ao deletar.");
            }

        })
        .then(loadUserExpenses)
        .catch(error => alert("Erro ao deletar despesa."));
}

// ----------------------
// BUSCA
// ----------------------

function searchExpenses() {

    const inputValue = document
        .getElementById("search-input")
        .value
        .toLowerCase()
        .trim();

    if (!inputValue) {
        loadUserExpenses();
        return;
    }

    const token = getToken();

    fetch(`${API_URL}/users/expenses`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    })
        .then(res => res.json())
        .then(data => {

            let filtered = [];

            if (inputValue.startsWith(">")) {

                const amount = inputValue.substring(1).trim();

                filtered = data.filter(e => e.amount > Number(amount));

            } else if (/^category\/\d+$/.test(inputValue)) {

                const categoryId = inputValue.split("/")[1];

                filtered = data.filter(e => e.category.id === Number(categoryId));

            } else if (!isNaN(inputValue)) {

                filtered = data.filter(e => e.id === Number(inputValue));

            } else {

                filtered = data.filter(e =>
                    e.description.toLowerCase().includes(inputValue) ||
                    e.category.name.toLowerCase().includes(inputValue)
                );

            }

            renderExpense(filtered);

        });
}

// ----------------------
// LIMPAR FORMULÁRIO
// ----------------------

function clearForm() {

    document.getElementById("description").value = "";
    document.getElementById("amount").value = "";
    document.getElementById("category").value = "";

}

// ----------------------
// SANITIZE
// ----------------------

function sanitize(input) {

    const div = document.createElement("div");
    div.textContent = input;

    return div.innerHTML;

}

// ----------------------
// LOGOUT
// ----------------------

function logout() {

    localStorage.removeItem("token");

    window.location.replace("login.html");

}

// ----------------------
// EXPORT GLOBAL
// ----------------------

window.addExpense = addExpense;
window.Delete = Delete;
window.Update = Update;
window.searchExpenses = searchExpenses;
window.logout = logout;