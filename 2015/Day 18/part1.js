const reader = require("../utils/reader");

const main = (data) => {
    const num_iter = 100;
    let configuration = data.map(entry => entry.split(""));
    for (let i = 0; i < num_iter; i++) {
        configuration = getNextConfiguration(configuration);
    }
    const count = getConfigOnCount(configuration);
    console.log(count);
}

const getNextConfiguration = (configuration) => {
    const new_configuration = [];
    for (let i = 0; i < configuration.length; i++) {
        new_configuration.push([]);
        for (let j = 0; j < configuration[i].length; j++) {
            const cell_value = getCellValue(configuration, i, j);
            if (cellIsOn(cell_value)) {
                const num_on_neighbors = getNumOnNeighbors(configuration, i, j);
                new_configuration[i].push((num_on_neighbors === 2 || num_on_neighbors === 3) ? "#" : ".");
            } else {
                new_configuration[i].push(getNumOnNeighbors(configuration, i, j) === 3 ? "#" : ".");
            }
        }
    }
    return new_configuration;
}

const getNumOnNeighbors = (configuration, i, j) => {
    let count = 0;
    count += cellIsOn(getCellValue(configuration, i-1, j-1));
    count += cellIsOn(getCellValue(configuration, i-1, j));
    count += cellIsOn(getCellValue(configuration, i-1, j+1));
    count += cellIsOn(getCellValue(configuration, i, j-1));
    count += cellIsOn(getCellValue(configuration, i, j+1));
    count += cellIsOn(getCellValue(configuration, i+1, j-1));
    count += cellIsOn(getCellValue(configuration, i+1, j));
    count += cellIsOn(getCellValue(configuration, i+1, j+1));
    return count;
}

const getConfigOnCount = (configuration) => {
    let count = 0;
    configuration.forEach(row => {
        row.forEach(cell => {
            count += cellIsOn(cell) ? 1 : 0;
        })
    });
    return count;
}

const cellIsOn = cell => cell === "#" ? 1 : 0;

const getCellValue = (configuration, i, j) => (i < 0 || j < 0 || i >= configuration.length || j >= configuration.length) ? "." : configuration[i][j]

reader.readFile("input", main);

