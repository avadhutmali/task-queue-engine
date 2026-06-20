CREATE TABLE IF NOT EXISTS tasks(
    id UUID PRIMARY KEY,
    description TEXT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    queue_name VARCHAR(20) NOT NULL DEFAULT 'Normal',
    payload JSONB NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_task_status ON tasks(status);
CREATE INDEX IF NOT EXISTS idx_task_queue ON tasks(queue_name,status);