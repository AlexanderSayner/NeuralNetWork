#version 330 core

out vec4 color;

in DATA
{
    vec2 textire_coordinates;
    vec3 position;
} fg_in;

uniform sampler2D tex;
uniform vec2 bird;

void main()
{
    color = texture(tex, fg_in.textire_coordinates);
    color *= 3.0 / (length(bird - fg_in.position.xy) + 4.5) + 0.55;
}
