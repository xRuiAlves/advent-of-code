const fs = require("fs");
const readline = require("readline");

const readFile = (file_name, callback) => {
    let data = [];

    const rd = readline.createInterface({
        input: fs.createReadStream(file_name)
    });

    rd.on("line", (line) => {
        data.push(line);
    });

    rd.on("close", () => {
        callback(data);
    });
};

const hello = () => {
    console.log("Hello")
}


module.exports = {
    readFile,
    hello
}