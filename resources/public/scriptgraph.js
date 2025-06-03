    async function f(query) {
        try {
            const response = await fetch("./graphql", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({query: query}),
            });
            if (!response.ok) {
                throw new Error('Erro ao buscar contador');
            }
            return await response.json();
        } catch (error) {
            console.error('Erro:', error);
            return { contador: 0 };
        }
    }
    
    document.addEventListener('DOMContentLoaded', async () => {
        
        const atualizarContador = async () => {
            const { data } = await f("{ getCount }");
            document.getElementById('contador').textContent = data.getCount;
        };
        document.getElementById('incrementar').addEventListener('click',async () => {
            await f("mutation { increment }");
            atualizarContador();
        });
        document.getElementById('decrementar').addEventListener('click',async () => {
            await f("mutation { decrease }");
            atualizarContador();
        });
        document.getElementById('resetar').addEventListener('click',async () => {
            await f("mutation { reset }");
            atualizarContador();
        });
        atualizarContador();
    });