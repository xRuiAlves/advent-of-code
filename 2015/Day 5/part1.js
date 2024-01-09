const reader = require("../utils/reader");

const main = (data) => {
    let num_nice_strings = 0;
    data.forEach(string => {
        let num_vowels = isVowel(string[0]) ? 1 : 0;
        let twice_occurance = false;
        for (let i = 0; i < string.length - 1; i++) {
            if (isBadSequence(string[i] + string[i+1])) {
                return;
            }
            if (isVowel(string[i+1])) {
                num_vowels++;
            }
            if (string[i] === string[i+1]) {
                twice_occurance = true;
            }
        }
        if (num_vowels >= 3 && twice_occurance) {
            num_nice_strings++;
        }
    });
    console.log(num_nice_strings)
}

const isVowel = (char) => (
    char === "a" || char === "e" || char === "i" || char === "o" || char === "u"
);

const isBadSequence = (sequence) => (
    sequence === "ab" || sequence === "cd" || sequence === "pq" || sequence === "xy"
);

reader.readFile("input", main);