const reader = require("../utils/reader");

const target_size = 150;
const main = (data) => {
    const containers = data.map(entry => parseInt(entry));
    const num_container_combinations = getNumContainerCombinations(containers);
    console.log(num_container_combinations);
}

const getNumContainerCombinations = (containers) => {
    let count = 0;
    for (let i = 0; i < Math.pow(2, containers.length); i++) {
        const occurences = getOccurences(i, containers.length);
        if (internalProduct(containers, occurences) === target_size) {
            count++;
        }
    }
    return count;
}

const internalProduct = (v1, v2) => v1.reduce((prev, curr, i) => prev+curr*v2[i], 0);

const getOccurences = (number, num_containers) => 
    (((Math.pow(2, num_containers) + number).toString(2)).substring(1, num_containers+1)).split("").map(num => parseInt(num))

reader.readFile("input", main);

