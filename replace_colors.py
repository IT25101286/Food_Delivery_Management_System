import os
import re

files = [
    "src/main/resources/templates/admin/dashboard.html",
    "src/main/resources/templates/admin/restaurant-form.html",
    "src/main/resources/templates/admin/restaurants.html",
    "src/main/resources/templates/login.html",
    "src/main/resources/templates/restaurants.html"
]

for f_path in files:
    if not os.path.exists(f_path):
        continue
    with open(f_path, 'r') as f:
        content = f.read()

    # Base Colors
    content = content.replace("bg-slate-950", "bg-[#0F1115]")
    content = content.replace("bg-slate-900", "bg-[#1A1D23]")
    content = content.replace("from-slate-900", "from-[#1A1D23]")
    content = content.replace("to-slate-950", "to-[#0F1115]")
    content = content.replace("bg-slate-800", "bg-white/5")
    content = content.replace("hover:bg-slate-800", "hover:bg-white/5")
    content = content.replace("bg-slate-700", "bg-white/10")
    
    # Text Primary
    content = content.replace("text-white", "text-[#F5F5F5]")
    
    # Text Secondary
    content = re.sub(r'text-gray-[0-9]{3}', 'text-[#A8A8A8]', content)
    
    # Hover borders/shadows (mostly accent)
    content = re.sub(r'hover:border-(?:cyan|blue|green|yellow|red)-[0-9]{3}(?:/[0-9]+)?', 'hover:border-[#C9A14A]', content)
    content = re.sub(r'hover:shadow-(?:cyan|blue|green|yellow|red)-[0-9]{3}(?:/[0-9]+)?', 'hover:shadow-[#C9A14A]/20', content)

    # Dividers/Borders explicitly mapped (normal state) - avoid capturing hover:
    # Use negative lookbehind so we don't match hover:border
    content = re.sub(r'(?<!hover:)border-(?:cyan|slate|blue|green|yellow|red)-[0-9]{3}(?:/[0-9]+)?', 'border-[rgba(255,255,255,0.08)]', content)
    content = re.sub(r'(?<!hover:)divide-(?:cyan|slate|blue|green|yellow|red)-[0-9]{3}(?:/[0-9]+)?', 'divide-[rgba(255,255,255,0.08)]', content)

    # Accent Text & Elements
    # Replace all cyan and blue with the Muted Gold accent
    content = re.sub(r'(?<!hover:)text-(?:cyan|blue)-[0-9]{3}', 'text-[#C9A14A]', content)
    content = re.sub(r'(?<!hover:)bg-(?:cyan|blue)-[0-9]{3}(?!/)', 'bg-[#C9A14A]', content)
    content = re.sub(r'(?<!hover:)bg-(?:cyan|blue)-[0-9]{3}/([0-9]+)', r'bg-[#C9A14A]/\1', content)
    
    content = re.sub(r'(?<!hover:)from-(?:cyan|blue)-[0-9]{3}(?!/)', 'from-[#C9A14A]', content)
    content = re.sub(r'(?<!hover:)from-(?:cyan|blue)-[0-9]{3}/([0-9]+)', r'from-[#C9A14A]/\1', content)
    
    content = re.sub(r'(?<!hover:)to-(?:cyan|blue)-[0-9]{3}(?!/)', 'to-[#C9A14A]', content)
    content = re.sub(r'(?<!hover:)to-(?:cyan|blue)-[0-9]{3}/([0-9]+)', r'to-[#C9A14A]/\1', content)
    
    # Hover states for accents
    content = re.sub(r'hover:text-(?:cyan|blue)-[0-9]{3}', 'hover:text-[#C9A14A]', content)
    content = re.sub(r'hover:bg-(?:cyan|blue)-[0-9]{3}(?!/)', 'hover:bg-[#C9A14A]', content)
    content = re.sub(r'hover:from-(?:cyan|blue)-[0-9]{3}', 'hover:from-[#C9A14A]', content)
    content = re.sub(r'hover:to-(?:cyan|blue)-[0-9]{3}', 'hover:to-[#C9A14A]', content)

    # Write back
    with open(f_path, 'w') as f:
        f.write(content)

print("Colors replaced successfully.")
