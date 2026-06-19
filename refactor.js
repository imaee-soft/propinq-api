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
            if (content.includes('@RequestMapping("/api/v1/') && !p.includes('Endpoints.java')) {
                content = content.replace(/@RequestMapping\("\/api\/v1\/(.*?)"\)/g, '@RequestMapping(Endpoints.API_V1 + "/$1")');
                
                // Only add import if not already there
                if (!content.includes('import com.imaee.propinq.config.utils.Endpoints;')) {
                    // Find the line after package declaration to insert the import
                    content = content.replace(/package(.*?);\r?\n/, 'package$1;\n\nimport com.imaee.propinq.config.utils.Endpoints;\n');
                }
                
                fs.writeFileSync(p, content, 'utf8');
                console.log('Updated ' + p);
            }
        }
    }
}

walk('./src/main/java');
