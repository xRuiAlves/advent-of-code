const reader = require("../utils/reader");

const NUM_ROWS = 50;
const NUM_COLS = 6;
const screen = Array(NUM_COLS).fill().map(_ =>Array(NUM_ROWS).fill("."));
const main = (data) => {
    data.forEach(instruction => {
        executeInstruction(instruction);
    });
    console.log(countLit());
}

const countLit = () => {
    let count = 0;
    screen.forEach(row => {
        row.forEach(light => {
            count += light === "#" ? 1 : 0;
        })
    })
    return count;
}

const executeInstruction = (instruction) => {
    if (instruction.substr(0,4) === "rect") {
        const [w, h] = instruction.substring(5, instruction.length).split("x").map(num => parseInt(num));
        for (let i = 0; i < h; i++) {
            for (let j = 0; j < w; j++) {
                screen[i][j] = "#";
            }
        }
    } else if (instruction.substr(7,3) === "row") {
        let rotate_amount = parseInt(instruction.split(" ").pop());
        let row_index = parseInt(instruction.substring(instruction.indexOf("=")+1, instruction.length));
        while (rotate_amount-- > 0) {
            const temp = screen[row_index][NUM_ROWS-1];
            for (let i = NUM_ROWS - 1; i > 0; i--) {
                screen[row_index][i] = screen[row_index][i-1];
            }
            screen[row_index][0] = temp;
        }
    } else {
        let rotate_amount = parseInt(instruction.split(" ").pop());
        let col_index = parseInt(instruction.substring(instruction.indexOf("=")+1, instruction.length));
        while (rotate_amount-- > 0) {
            const temp = screen[NUM_COLS-1][col_index];
            for (let i = NUM_COLS - 1; i > 0; i--) {
                screen[i][col_index] = screen[i-1][col_index];
            }
            screen[0][col_index] = temp;
        }
    }
}

reader.readFile("input", main);