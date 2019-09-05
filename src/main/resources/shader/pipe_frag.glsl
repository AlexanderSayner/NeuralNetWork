#version 330 core

out vec4 color;

in DATA
{
    vec2 textire_coordinates;
    vec3 position;
} fg_in;

uniform sampler2D tex;
uniform int top;
uniform vec2 pipe_light;

void main()
{
    vec2 tc = fg_in.textire_coordinates;
    if (top == 1)
        tc.y = 1.0 - fg_in.textire_coordinates.y;

    color = texture(tex, tc);

    if (color.w < 1.0)
        discard;

    color *= 3.0 / (length(pipe_light - fg_in.position.xy) + 4.5) + 0.55;
    color.w = 1.0;
}
