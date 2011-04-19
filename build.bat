@title openRS Build Script
@echo off

echo Building openRS...
echo --------------------------------------------------------------------------------
call ant compile
call ant deploy
echo --------------------------------------------------------------------------------
echo Build complete!

pause