🚀 Expense Controll
<p align="center"> <img src="https://img.shields.io/badge/Expense%20Controll-212121.svg?style=for-the-badge&logo=graph&logoColor=white" height="90"/> </p> <p align="center"><b>Sistema completo para gerenciamento de despesas pessoais</b></p> <p align="center"> <img src="https://img.shields.io/badge/Status-Online-brightgreen?style=flat-square"/> <img src="https://img.shields.io/badge/Backend-Render-blue?style=flat-square"/> <img src="https://img.shields.io/badge/Frontend-Netlify-blueviolet?style=flat-square"/> <img src="https://img.shields.io/badge/Database-Neon-orange?style=flat-square"/> </p> <p align="center"> <img src="https://img.shields.io/badge/Java-21-007396?style=for-the-badge&logo=openjdk&logoColor=white"/> <img src="https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/> <img src="https://img.shields.io/badge/PostgreSQL-Neon-4169E1?style=for-the-badge&logo=postgresql&logoColor=white"/> </p>
🌐 Live Demo
<p align="center"> 👉 <a href="https://expensecontroll.netlify.app/">Acessar o Frontend</a> • 👉 <a href="https://expense-control-project.onrender.com/expenses">API Online</a> </p>
📘 Sobre o Projeto

O Expense Controll é um sistema completo para controle de despesas pessoais.
Inclui:

✅ Backend em Spring Boot
✅ Banco PostgreSQL hospedado no Neon
✅ Frontend em HTML/CSS/JS hospedado no Netlify
✅ Deploy do backend no Render

O sistema permite cadastrar, listar, atualizar, buscar e excluir despesas de forma simples e eficiente.

🛠️ Tecnologias Utilizadas
🖥️ Frontend

HTML5

CSS3

JavaScript (Vanilla)

Consumo de API com fetch()

⚙️ Backend

Java 21

Spring Boot 3

Spring Data JPA

Maven

PostgreSQL (Neon)

🎯 Funcionalidades

✔ Visualizar despesas
✔ Adicionar novas despesas
✔ Atualizar informações
✔ Excluir despesas
✔ Buscar por:

ID

Categoria

Valor maior que X

✔ Totalizador automático do valor das despesas
✔ Interface simples e funcional

🔍 Busca Inteligente

O campo de busca aceita múltiplos padrões:

Busca	Exemplo	Resultado
ID	2	Retorna despesa de ID 2
Categoria	category/3	Lista despesas da categoria 3
Valor maior que	>500	Lista despesas maiores que 500
🖼️ Preview da Aplicação
<p align="center"> <img src="frontend/docs/home.png" width="80%"/> </p>
📂 Estrutura do Projeto
Expense-Control-Project/
│
├── backend/
│   ├── src/
│   ├── pom.xml
│   ├── mvnw / mvnw.cmd
│   └── ...
│
├── frontend/
│   ├── index.html
│   ├── insert.html
│   ├── style.css
│   └── script.js
│
└── README.md

🔗 Integração Frontend + Backend

O frontend se comunica com a API por meio de:

const API_URL = "https://expense-control-project.onrender.com/expenses";


Operações realizadas:

GET → buscar despesas

POST → criar nova despesa

PUT → atualizar registro

DELETE → excluir despesa

📌 Status do Projeto
Item	Status
Backend	✔ Concluído
Banco de Dados	✔ Hospedado e integrado
Frontend	✔ Publicado
Deploy	✔ Online
Melhorias Futuras	🔄 Aceitando evoluções
👨‍💻 Autor

Abimael Abreu
Desenvolvedor Java | Spring Boot | Frontend Web
