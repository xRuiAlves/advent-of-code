const reader = require("../utils/reader");

const main = (data) => {
    const blocked_ips = data.map(entry => {
        const ips = entry.split("-").map(num => parseInt(num));
        return {
            low: ips[0],
            high: ips[1]
        };
    }).sort((a, b) => a.low - b.low);
    
    let curr_lower = 0;
    blocked_ips.forEach(range => {
        if (curr_lower < range.low) {
            console.log(curr_lower);
            process.exit(0);
        } else {
            curr_lower = Math.max(curr_lower, range.high + 1);
        }
    });
}

reader.readFile("input", main);