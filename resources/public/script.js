    async function f(target, method) {
        try {
            const response = await fetch(target, {
                method: method
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
            const contador = await f('./api/', 'GET');
            document.getElementById('contador').textContent = contador;
        };
        document.getElementById('incrementar').addEventListener('click',async () => {
            await f('./api/increment', 'POST');
            atualizarContador();
        });
        document.getElementById('decrementar').addEventListener('click',async () => {
            await f('./api/decrease', 'POST');
            atualizarContador();
        });
        document.getElementById('resetar').addEventListener('click',async () => {
            await f('./api/reset', 'POST');
            atualizarContador();
        });
        atualizarContador();
    });