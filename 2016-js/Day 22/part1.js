const reader = require("../utils/reader");

const main = (data) => {
    data.shift();
    data.shift();
    const nodes = data.map(entry => {
        const fields = entry.replace(/\s+/g, ' ').split(" ");
        return {
            name: fields[0],
            size: parseInt(fields[1], 10),
            used: parseInt(fields[2], 10),
            available: parseInt(fields[3], 10)
        }
    });
    
    const nodes_by_available = [...nodes].sort((a, b) => b.available - a.available);
    const nodes_by_used = [...nodes].sort((a, b) => a.used - b.used);

    let count = 0;
    for (const a_node of nodes_by_available) {
        for (const u_node of nodes_by_used) {
            if (a_node.name === u_node.name || u_node.used === 0) continue;

            if (a_node.available >= u_node.used) count++;
            else break;
        }
    }
    console.log(count);
}

reader.readFile("input", main);