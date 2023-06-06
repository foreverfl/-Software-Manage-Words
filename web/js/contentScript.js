// meanings
let words = new Array();
let pronounces = new Array();
let meanings = new Array();

const target = document;

const observer = new MutationObserver(function (mutations) {
    mutations.forEach(function (mutation) {

        if (mutation.target.id === 'section_word_card') {
            const section = mutation.target;
            console.log(section);
            const cards = section.querySelectorAll('.card_word');

            for (let i = 0; i < cards.length; i++) {
                // word
                const word_1 = cards[i].querySelector('a.title');
                const word_2 = word_1.innerText;
                const word_3 = word_2.replaceAll(/\·/g, '');
                words[i] = word_3;

                // pronounces
                const pronounce_1 = cards[i].querySelector('span.pronounce');
                if (pronounce_1 !== null) {
                    const pronounce_2 = pronounce_1.innerText;
                    pronounces[i] = pronounce_2;
                } else {
                    pronounces[i] = '-';
                }

                // meanings
                const meanings_1 = cards[i].querySelector('ul.list_mean');
                const meanings_2 = meanings_1.innerText;
                meanings[i] = meanings_2;

            }

            // sending Data
            let data = new Object();
            data.words = words;
            data.pronounces = pronounces;
            data.meanings = meanings;
            let dataJson = JSON.stringify(data);

            console.log(dataJson);

            chrome.runtime.sendMessage({ type: "setData", data: dataJson });

        }


    });
});



const config = {
    childList: true,
    subtree: true,
    characterData: true
};

observer.observe(target, config);

