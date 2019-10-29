import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DccCommand } from './dcc-command'
import { DccStatus } from './dcc-status';

@Component({
    selector: 'app-dcc',
    templateUrl: './dcc.component.html',
    styleUrls: ['./dcc.component.css']
})
export class DccComponent implements OnInit {

    dccCommand: DccCommand;

    dccStatus: DccStatus;

    /**
     * Custom constructor.
     */
    constructor(
        private http: HttpClient
    ) {
        this.dccCommand = new DccCommand()
        this.statusUpdate();
    }

    ngOnInit() {
    }

    /**
     * Execute the DCC Command
     */
    executeCommand() {
        if (this.dccCommand.command != null && this.dccCommand.command != "") {

            // post
            this.http.post('/dcc', this.dccCommand)
                .subscribe(
                    data => console.log("Success: " + data),
                    error => console.log("Error: " + error)
                )
        }
    }

    switchOn() {
        this.http.post('/dcc/on', this.dccCommand)
            .subscribe(
                data => console.log("Success: " + data),
                error => console.log("Error: " + error)
            )
    }

    switchOff() {
        this.http.post('/dcc/off', this.dccCommand)
            .subscribe(
                data => console.log("Success: " + data),
                error => console.log("Error: " + error)
            )
    }

    statusUpdate() {
        setTimeout(() => {
            // update status
            this.http.get<DccStatus>('/dcc/status')
                .subscribe(
                    data => this.dccStatus = data,
                    error => console.log("Error: " + error)
                )

            this.statusUpdate();
        }, 2500);
    }
}
