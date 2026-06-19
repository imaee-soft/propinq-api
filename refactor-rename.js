const fs = require('fs');
const path = require('path');

function walk(dir) {
    const files = fs.readdirSync(dir);
    for (const file of files) {
        const p = path.join(dir, file);
        if (fs.statSync(p).isDirectory()) {
            walk(p);
        } else if (p.endsWith('.java')) {
            let content = fs.readFileSync(p, 'utf8');
            if (content.includes('Endpoints.API_V1')) {
                content = content.replace(/Endpoints\.API_V1/g, 'Endpoints.API');
                fs.writeFileSync(p, content, 'utf8');
                console.log('Updated ' + p);
            }
        }
    }
}

walk('./src/main/java');
