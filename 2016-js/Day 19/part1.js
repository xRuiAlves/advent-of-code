const reader = require("../utils/reader");

const main = (data) => {
    const num_elves = parseInt(data[0]);
    const elves = [];
    for (let i = 0; i < num_elves; i++) {
        elves.push({
            num: i,
            presents: 1,
            next: i+1
        })
    }
    elves[num_elves-1].next = 0;
    
    let elf = 0;
    while (elves[elf].next !== elf) {
        elves[elf].next = elves[elves[elf].next].next;
        elf = elves[elf].next;
    }

    console.log(elves[elf].num + 1);
}

reader.readFile("input", main);