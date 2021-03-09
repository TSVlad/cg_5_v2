#version 120
varying vec4 color;
uniform float t;

void main() {
    vec4 coord = gl_Vertex;
    coord.y = coord.y * cos(t + coord.y)
    gl_Position = gl_ModelViewMatrix * coord;

    color = gl_Color;


//    vec4 position_in_world_space  = osg_ViewMatrixInverse * position_in_view_space;
//    position_in_world_space.y = position_in_view_space.y * cos(t + position_in_world_space.y)
//    position_in_view_space = osg_ViewMatrix * position_in_world_space;
//    vec4 position_in_object_space = gl_ModelViewMatrixInverse * position_in_view_space;
//    gl_Position = gl_ModelViewProjectionMatrix * position_in_object_space;


//    float e = exp(theta)/(1 + exp(theta)); // сигмоида
//    color.r = e;
//    color.g = 1.0 - e;
//    color.b = 1.0 - e;
}