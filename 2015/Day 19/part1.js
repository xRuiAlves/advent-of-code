const reader = require("../utils/reader");

const main = (data) => {
    const input_molecule = data[data.length-1];
    const decomposed_molecule = decomposeMolecule(input_molecule);
    const decompositions = {};
    for (let i = 0; i < data.length - 2; i++) {
        const [lhs, _, rhs]= data[i].split(" ");
        if (!decompositions[lhs]) {
            decompositions[lhs] = [];
        }
        decompositions[lhs].push(rhs);
    }

    const productions = new Set();
    for (let i in decomposed_molecule) {
        const atom = decomposed_molecule[i];
        decompositions[atom] && decompositions[atom].forEach(production => {
            const new_molecule = [...decomposed_molecule];
            new_molecule[i] = production;
            productions.add(new_molecule.join(""));
        });
    }
    console.log(productions.size);
}

const decomposeMolecule = (molecule) => {
    const atoms = [];
    for (let i = 0; i < molecule.length; i++) {
        if (i < molecule.length-1 && isLowerCase(molecule[i+1])) {
            atoms.push(molecule.substr(i, 2));
            i++;
        } else {
            atoms.push(molecule[i]);
        }
    }
    return atoms;
}

const isLowerCase = (char) => char.toLowerCase() === char;

reader.readFile("input", main);

