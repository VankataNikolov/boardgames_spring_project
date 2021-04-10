export default function (cssClassName, step) {
    const list = document.getElementsByClassName(cssClassName);

    let initDelay = 0;

    Array.from(list)
        .forEach(item => {
            item.style['animation-delay'] = initDelay.toString() + 's';
            initDelay += step;
        });
}

