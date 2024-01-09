const reader = require("../utils/reader");

const num_iterations = 50;
const main = (data) => {
    let string = data[0];
    for (let i = 0; i < num_iterations; i++) {
        let current_char = "";
        let count = 0;
        let new_string = "";
        for (c of string) {
            if (c !== current_char) {
                if (count > 0) {
                    new_string += count + current_char;
                    count = 0;
                }
                current_char = c;
            }
            count++;
        }
        new_string += count + current_char;
        string = new_string;
    }
    console.log(string.length);
}
reader.readFile("input", main);