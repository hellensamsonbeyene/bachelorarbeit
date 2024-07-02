module.exports = {
    presets: [
        [
            '@babel/preset-env',
            {
                targets: {
                    node: 'current', // Umgebung angeben, für die Babel kompilieren soll
                },
            },
        ],
        '@babel/preset-react', // Für React und JSX-Unterstützung
    ],
    plugins: [
        '@babel/plugin-proposal-class-properties', // Für Klassenfelder
        '@babel/plugin-transform-runtime', // Für ES6-Features, die nicht vom Preset Env abgedeckt sind
    ],
};
