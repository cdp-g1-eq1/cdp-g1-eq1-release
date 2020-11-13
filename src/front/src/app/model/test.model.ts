export class Test{
    private id: number;
    private name: string;
    private description: string;
    private lastExecution: Date;
    private state: string;
    private projectId: number;

    constructor(name: string, description: string, lastExecution: Date, state: string, projectId: number, id?: number) {
        this.name = name;
        this.description = description;
        this.lastExecution = lastExecution;
        this.state = state;
        this.projectId = projectId;
        this.id = (id) ? id : -1;
    }

    static fromJSON(json): Test {
        return new Test(
            json.name,
            json.description,
            json.lastExecution,
            json.state,
            json.projectId,
            json.id
        );
    }

    public getId(): number {
        return this.id;
    }


    public getName(): string {
        return this.name;
    }


    public getDescription(): string {
        return this.description;
    }


    public getLastExecution(): Date {
        return this.lastExecution;
    }


    public getState(): string {
        return this.state;
    }


    public getProjectId(): number {
        return this.projectId;
    }

    public setName(value: string): void {
        this.name = value;
    }

    public setState(state: string): void {
        this.state = state;
    }

    public setLastExecution(date: Date): void {
        this.lastExecution = date;
    }
}
