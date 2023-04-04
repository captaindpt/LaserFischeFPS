$rootPath = "C:\Users\98910\Documents\NetBeansProjects\Application"
$maxLayers = 3
$maxDepth = 5
$maxLines = 100
$minLines = 10

function GenerateFolder($path, $depth) {
    if ($depth -gt $maxDepth) {
        return
    }
    New-Item -ItemType Directory -Path $path
    $numDirs = Get-Random -Minimum 0 -Maximum 5
    $numFiles = Get-Random -Minimum 0 -Maximum 5
    for ($i = 0; $i -lt $numDirs; $i++) {
        $dirName = "dir$($i + 1)"
        $dirPath = Join-Path $path $dirName
        GenerateFolder $dirPath ($depth + 1)
    }
    for ($i = 0; $i -lt $numFiles; $i++) {
        $fileName = "file$($i + 1).txt"
        $filePath = Join-Path $path $fileName
        $numLines = Get-Random -Minimum $minLines -Maximum $maxLines
        $lines = 1..$numLines | ForEach-Object {Get-Random -InputObject ('apple', 'banana', 'cherry', 'date', 'elderberry', 'fig', 'grape', 'honeydew', 'kiwi', 'lemon', 'mango', 'nectarine', 'orange', 'pear', 'quince', 'raspberry', 'strawberry', 'tangerine', 'watermelon') -Count (Get-Random -Minimum 1 -Maximum 10)}
        $content = $lines -join "`r`n"
        Set-Content $filePath $content
    }
}

GenerateFolder $rootPath 0
