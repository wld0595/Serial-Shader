#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;
uniform float time;

void main() {
    vec3 color=texture2D(u_texture,v_texCoords+0.005*vec2(sin(v_texCoords.x*1024.0+time),cos(v_texCoords.y*768.0+time))).rgb;
    vec3 movescale=vec3(color);

    gl_FragColor = vec4(movescale, 1.0);
}