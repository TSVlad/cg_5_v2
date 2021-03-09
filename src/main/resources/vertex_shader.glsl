#version 120
varying vec4 color;
uniform float t;

void main() {
    vec4 coord = gl_Vertex;
    coord.y = coord.y * cos(t + coord.y);
    gl_Position = gl_ModelViewMatrix * coord;

    color = gl_Color;

    float e = exp(coord.y * 3)/(1 + exp(coord.y * 3));
    color.r = e;
    color.g = 1.0 - e;
    color.b = e;
}