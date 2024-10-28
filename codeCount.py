import os

def count_lines_in_directory(directory):
    total_lines = 0
    for root, dirs, files in os.walk(directory):
        for file in files:
            if file.endswith(".java"):  # Vous pouvez adapter cette extension à votre besoin
                file_path = os.path.join(root, file)
                with open(file_path, 'r', encoding='utf-8') as f:
                    lines = f.readlines()
                    total_lines += len(lines)
                    print(f'{file}: {len(lines)} lines')

    return total_lines

# Exemple d'utilisation
directory = 'C:/Users/mynosci/IdeaProjects/prison/src'  # Remplacez par le chemin de votre dossier
total = count_lines_in_directory(directory)
print(f'Total de lignes dans le dossier : {total}')
