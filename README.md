To compile this one will need to download JLaTeXMath jar from

http://forge.scilab.org/index.php/p/jlatexmath/downloads/693/

To run the server use
```
java Server
```
To run the client use
```
java GuiClient
```

Once connected you can send

- messages
- private messages (with /w)
- latex messages (/latex)

Demo latex message: /latex x^2=4
Will open a new window where it will render that formula.

It has lots of bugs. Most annoying:
- if you close the latex window after the first message the next messages will just open an empty window
- some formulas throw erros (eg.: /latex x=\\frac{-b \\pm \\sqrt {b^2-4ac}}{2a} )
- some elements are not rendered correct (eg.: /latex \\frac{a}{b} )
ETC.

