#version 330 core

out vec4 color;

in DATA
{
    vec2 textire_coordinates;
} fs_in;

uniform sampler2D tex;

void main()
{
    color = texture(tex, fs_in.textire_coordinates);
}
