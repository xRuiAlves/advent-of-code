const reader = require("../utils/reader");
const tape = {
    children: 3,
    cats: 7,
    samoyeds: 2,
    pomeranians: 3,
    akitas: 0,
    vizslas: 0,
    goldfish: 5,
    trees: 3,
    cars: 2,
    perfumes: 1
}

const main = (data) => {
    const aunts = [];
    data.forEach(entry => {
        const aunt = {};
        entry = entry.split(" ");
        for (let i = 2; i < entry.length - 1; i += 2) {
            const compound = entry[i].substr(0, entry[i].length - 1);
            const amount = parseInt(entry[i+1]);
            aunt[compound] = amount
        }
        aunts.push(aunt);
    });
    
    const good_aunt = getGoodAunt(aunts);
    console.log(good_aunt);
}

const getGoodAunt = (aunts) => {
    const good_aunts = [];
    for (let i in aunts) {
        if (isAuntGood(aunts[i])) {
            return ++i;
        }
    };
    return good_aunts;
}

const isAuntGood = (aunt) => {
    let good_aunt = true;
    Object.keys(aunt).forEach(compound => {
        if (aunt[compound] != tape[compound]) {
            good_aunt = false;
        }
    });
    return good_aunt;
}


reader.readFile("input", main);
