import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogClose, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatDividerModule } from '@angular/material/divider';
import { CreateGroupInterface } from '../../../interfaces/group';
import { GroupService } from '../../../services/group/group.service';
import { JwtHandlerService } from '../../../services/jwt/JwtHandler.service';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { RatingComponent } from '../rating/rating.component';

@Component({
  selector: 'app-new-group',
  templateUrl: './new-group.component.html',
  standalone: true,
  imports: [MatCardModule, MatDialogTitle, MatDividerModule, MatDialogContent, MatDialogActions, MatSelectModule, MatCheckboxModule, MatIconModule, MatButtonModule, MatDividerModule, MatFormFieldModule, FormsModule, ReactiveFormsModule,MatInputModule],
  styleUrls: ['./new-group.component.css']
})
export class NewGroupComponent implements OnInit {

  newGroupForm: FormGroup;
  group:CreateGroupInterface;
  snackBar: any;
  router: any;

  constructor(private build: FormBuilder, 
                private service: GroupService, 
                private jtwService: JwtHandlerService,
                private dialogRef: MatDialogRef<RatingComponent>) {

    
    this.newGroupForm = this.build.group({
      name: ['', [Validators.required]],
    });

    this.group = {
      name: "",
      creatorId: ""
    }

  }

  ngOnInit() {
  }

  createGroup(): void {
    this.group = this.newGroupForm.value;

    this.group.creatorId = this.jtwService.getUID()
    
    this.service.createGroup(this.group).subscribe(
      resp => {
        console.log(resp);
        this.router.navigate(['/group/'+resp.id]);
        

      }, err => {
        console.log(err);
        this.snackBar.open(err.error.message, 'Close', { duration: 5000, panelClass: 'snackbar'});
      }
    );
  }

  cancel() {
    this.dialogRef.close()
  }

}
