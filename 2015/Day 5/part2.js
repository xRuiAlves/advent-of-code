const reader = require("../utils/reader");

const main = (data) => {
    let num_nice_strings = 0;
    data.forEach(string => {
        let assertion1 = false;
        let assertion2 = false;
        for (let i = 0; i < string.length - 2; i++) {
            if (string[i] === string[i+2]) {
                assertion1 = true;
                break;
            }
        }

        for (let i = 0; i < string.length - 1; i++) {
            for (let j = i+2; j < string.length - 1 ; j++) {
                if (string[i] === string[j] && string[i+1] === string[j+1]) {
                    assertion2 = true;
                    break;
                }
            }
        }

        if (assertion1 && assertion2) {
            num_nice_strings++;
        }
    });
    console.log(num_nice_strings)
}

reader.readFile("input", main);