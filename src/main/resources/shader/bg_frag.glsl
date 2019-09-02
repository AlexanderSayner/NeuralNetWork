#version 330 core

out vec4 color;

in DATA
{
    vec2 textire_coordinates;
} fg_in;

uniform sampler2D tex;

void main()
{
    color = texture(tex, fg_in.textire_coordinates);
//    color = vec4(1.0,0.0,0.0,1.0);
}
