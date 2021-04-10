const gameCards = document.getElementsByClassName("animated-card");

let step = 0.2;
let initDelay = 0;

Array.from(gameCards)
    .forEach(gc => {
        gc.style['animation-delay'] = initDelay.toString() + 's';
        initDelay += step;
    });