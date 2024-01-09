const reader = require("../utils/reader");

const main = (data) => {
    const lower_bound = parseInt(data[0]);
    const num_houses = lower_bound/10;
    const houses = new Array(num_houses ).fill(0); 
    for (let elf_num = 1; elf_num <= num_houses; elf_num++) {
        for (let house_num = elf_num; house_num <= num_houses; house_num += elf_num) {
            houses[house_num-1] += elf_num*10;
        }
    }
    for (let i in houses) {
        if (houses[i] > lower_bound) {
            console.log(++i);
            return;
        }
    }
}

reader.readFile("input", main);

