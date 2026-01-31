const API_URL = "http://localhost:8080/expenses"
let expenseIdToUpdate = null;

document.addEventListener("DOMContentLoaded", loadExpenses);
document.addEventListener("DOMContentLoaded", () => {
    const  params = new URLSearchParams(window.location.search);
    const id = params.get("id");
    if(id){
        loadExpenseById(id);
    }

});

function loadExpenses(){
   fetch(API_URL)
   .then(response => response.json())
   .then(data => renderExpense(data))
   .catch(error => console.error("Error fetching expenses:", error));
}

function renderExpense(expenses){
    const tableBody = document.getElementById("expense-list")
    const totalSpan = document.getElementById("total");

    tableBody.innerHTML = "";
    let total = 0;

    expenses.forEach(e => {
         
        const date = e.moment.split("T")[0];
        const tr = document.createElement("tr");
        tr.innerHTML = `
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
