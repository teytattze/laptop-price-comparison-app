import { app } from './app';
import { config } from './common/config';

const bootstrap = () => {
  const PORT = config.server.port || 8000;

  app.listen(PORT, () => {
    console.log(`Listening on port ${PORT}...`);
  });
};

bootstrap();
