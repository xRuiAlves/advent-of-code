const reader = require("../utils/reader");

const ingredients = [];
const num_spoons = 100;
let best_total_score = 0;
const main = (data) => {
    data.forEach(entry => {
        entry = entry.split(" ");
        ingredients.push({
            capacity: parseInt(entry[2]),
            durability: parseInt(entry[4]),
            flavour: parseInt(entry[6]),
            texture: parseInt(entry[8]),
            calories: parseInt(entry[10])
        });
    })

    buildSetup([], num_spoons);
    console.log(best_total_score);
}

const buildSetup = (setup, remainder) => {
    if (setup.length < ingredients.length - 1) {
        for (let i = remainder; i >= 0; i--) {
            setup.push(i);
            buildSetup(setup, remainder - i);
            setup.pop();
        }
    } else if (setup.length === ingredients.length - 1) {
        setup.push(remainder);
        evalSetup(setup);
        setup.pop();
    }
}

const evalSetup = (setup) => {
    let capacity = 0;
    let durability = 0;
    let flavour = 0;
    let texture = 0;
    let calories = 0;
    setup.forEach((count, i) => {
        capacity += ingredients[i].capacity * count;
        durability += ingredients[i].durability * count;
        flavour += ingredients[i].flavour * count;
        texture += ingredients[i].texture * count;
        calories += ingredients[i].calories * count;
    });
    if (calories === 500 && capacity > 0 && durability > 0 && flavour > 0 && texture > 0) {
        best_total_score = Math.max(best_total_score, capacity * durability * flavour * texture);
    }
}

reader.readFile("input", main);
