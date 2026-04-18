# OrbVault

OrbVault é um aplicativo Android desenvolvido em Kotlin com Jetpack Compose.

Criei o projeto para aplicar na prática conceitos como consumo de API, gerenciamento de estado e construção de interfaces declarativas.  

- [Tecnologias](#tecnologias-utilizadas)  
- [Funcionalidades](#funcionalidades)  
- [Demonstração](#demonstração)  
- [Tomadas de Decisões](#tomadas-de-decisões)  
- [Objetivo](#objetivo-do-projeto)  
- [Contato](#contato)  
- [Aviso Legal](#aviso-legal)  

## Tecnologias utilizadas

- [Kotlin](https://kotlinlang.org/) — Melhor linguagem de todas
- [Jetpack Compose](https://developer.android.com/jetpack/compose) — Criação rápida e simples da interface
- [Material 3](https://m3.material.io/) — Utilizado para manter consistência visual e boas práticas de UI
- Arquitetura MVVM — Organização do projeto utilizando ViewModel e StateFlow para gerenciamento de estado e separação de responsabilidades.
- [Retrofit](https://square.github.io/retrofit/) — Cliente HTTP para consumo da API REST.
- [Koin](https://insert-koin.io/) — Utilizado para gerenciar dependências do app de forma mais organizada e sem complicações.
- [Coil](https://coil-kt.github.io/coil/compose/) — Carregamento assíncrono de imagens via URL (AsyncImage)
- [Navigation](https://developer.android.com/jetpack/compose/navigation) — Gerenciamento de navegação entre telas.
- Gerenciamento de textos com `strings.xml` — Centralização dos textos do app, facilitando manutenção e futuras traduções

## Funcionalidades

### Países
* Busca de países pelo nome  
* Exibição de informações detalhadas do país:
  * Nome comum e oficial  
  * Capital  
  * Região e sub-região
  * Idioma
  * Moeda  
  * População formatada  
  * Fusos horários  
* Exibição da bandeira do país  

### Explore
* Top 10 países mais populosos  
* Top 10 maiores países (por área)  
* País aleatório com possibilidade de gerar novos resultados  
* Navegação entre diferentes categorias de exploração  

### Gerais
* Tratamento de estados de carregamento e erro com opção de retry

## Demonstração

Abaixo irei disponibilizar um vídeo mostrando como o aplicativo está:  
> https://github.com/user-attachments/assets/a1029e68-5e56-4f1d-a950-f6fbb35044cd

## Tomadas de Decisões

### Jetpack Compose
Optei por Jetpack Compose por já ter experiência com a abordagem declarativa, que torna a construção da interface mais organizada. Confesso que não sinto falta do XML ;)

### Arquitetura (MVVM)
Escolhi estruturar o projeto em MVVM para manter a lógica separada da interface.  
O uso de ViewModel com StateFlow permite que a interface reaja automaticamente às mudanças de estado.

### Consumo de API
Os dados dos países são obtidos por meio de uma API pública. Isso me permitiu praticar requisições HTTP, tratamento de dados e organização em camadas dentro do projeto.

> 🔗 Link da API: https://restcountries.com/

### Gerenciamento de Estado de UI (UiState)
Para lidar com os estados da tela (carregando, sucesso e erro), utilizei uma abordagem com sealed class (UiState) junto com StateFlow.
Com isso, a interface reage automaticamente às mudanças de estado, deixando o código mais organizado e fácil de entender.

### Formatação de Dados
Criei funções auxiliares para tratar dados vindos da API, como:
- moedas
- idiomas
- fusos horários  

Isso melhora a legibilidade das informações exibidas e a organização do projeto.

### Carregamento de Imagens
Utilizei a biblioteca Coil para carregar imagens das bandeiras via URL de forma assíncrona, garantindo uma experiência mais fluida e melhor desempenho na renderização das imagens.

### Material 3
Material 3 foi escolhido para manter um visual moderno e consistente, aproveitando componentes já bem estruturados.

### Navegação entre Telas (Navigation)
Para organizar a navegação entre as telas, utilizei o Navigation. Essa biblioteca facilita muito a definição de rotas e o gerenciamento da navegação.

## Objetivo do Projeto

Este projeto foi desenvolvido com o objetivo de:

* Praticar consumo de API REST  
* Evoluir na construção de interfaces usando Jetpack Compose  
* Melhorar a organização de código com MVVM  
* Trabalhar melhor com gerenciamento de estado utilizando ViewModel e StateFlow  
* Construir um projeto para portfólio  

## Contato

🔗 [LinkedIn](https://www.linkedin.com/in/gblrodrigues/)

## Aviso Legal

Este projeto foi desenvolvido exclusivamente para fins educacionais e de portfólio.

Os dados utilizados neste aplicativo são fornecidos por uma API pública:  
https://restcountries.com/
