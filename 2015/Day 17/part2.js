const reader = require("../utils/reader");

const target_size = 150;
const main = (data) => {
    const containers = data.map(entry => parseInt(entry));
    const container_combinations_counts = getNumContainerCombinations(containers);
    for (let count of container_combinations_counts) {
        if (count > 0) {
            console.log(count);
            return;
        }
    }
}

const getNumContainerCombinations = (containers) => {
    let counts = new Array(containers.length).fill(0);
    for (let i = 0; i < Math.pow(2, containers.length); i++) {
        const occurences = getOccurences(i, containers.length);
        if (internalProduct(containers, occurences) === target_size) {
            const num_containers = countContainers(occurences);
            counts[num_containers]++;
        }
    }
    return counts;
}

const countContainers = (occurences) => {
    let count = 0;
    for (let has of occurences) {
        count += has;
    }
    return count;
}

const internalProduct = (v1, v2) => v1.reduce((prev, curr, i) => prev+curr*v2[i], 0);

const getOccurences = (number, num_containers) => 
    (((Math.pow(2, num_containers) + number).toString(2)).substring(1, num_containers+1)).split("").map(num => parseInt(num))

reader.readFile("input", main);

