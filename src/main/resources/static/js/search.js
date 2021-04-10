const searchBar = document.getElementById("searchBar");
const searchBarContainer = document.getElementsByClassName("search-container")[0];
const baseUrl = "http://localhost:8080/games/";

let allGames = [];

fetch(baseUrl + 'api').
    then(response => response.json()).
    then(games => {
        for (let game of games) {
            allGames.push(game);
        }
    });

let timeout = null;
searchBar.addEventListener('keyup', function() {
    const text = searchBar.value.toLowerCase();
    clearTimeout(timeout);
    if(text.length !== 0){
        timeout = setTimeout(function() {
            let filteredGames = allGames.filter(game => {
                return game.name.toLowerCase().includes(text);
            });
            displayGameNames(filteredGames);
        }, 500)
    }
});

function displayGameNames(games) {
    if(searchBarContainer.getElementsByTagName('ul').length > 0){
        searchBarContainer.removeChild(searchBarContainer.getElementsByTagName('ul')[0]);
    }

    if(games.length !== 0){
        let listNames = createElem('ul',
            games.map(
                game => createElem('li',
                    [createElem('a', game.name,
                        {href: baseUrl + game.id + '/details'})])
            )
        );
        Array.from(listNames.getElementsByTagName('a'))
            .forEach(a => {
                a.style.textDecoration = 'none';
                a.style.color = 'black';
            });
        searchBarContainer.append(listNames);
    }

}

function createElem(type, content, attributes){
    const newElem = document.createElement(type);

    if(attributes !== undefined){
        Object.assign(newElem, attributes);
    }

    if(Array.isArray(content)){
        content.forEach(appendEl);
    } else {
        appendEl(content);
    }

    function appendEl(node){
        if(typeof node === 'string' || typeof node === 'number'){
            node = document.createTextNode(node);
        }
        newElem.appendChild(node);
    }
    return newElem;
}