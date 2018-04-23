select t.*,rowid from mm_purchase  t where t.create_date between date '2016-03-01' and date '2016-04-01'; 
select t.*,rowid from mm_return_deliverer t; 
select * from mm_purchase t  where rownum<=100 order by t.total_amount; 

create or replace procedure p_get_purchase_total_amount(beginDate varchar2,endDate varchar2,totalAmount out number) is 
begin 
  select sum(total_amount) into totalAmount from mm_purchase where create_date between to_date(beginDate, 'yyyy-mm-dd')  and to_date(endDate, 'yyyy-mm-dd');
end p_get_purchase_total_amount;

create or replace procedure p_get_sum is 
topNum number:=100;
curNum number:=1;
sumNum number:=0;
begin
  while curNum<topNum loop 
    sumNum:=sumNum+curNum;
    curNum:=curNum+1;
   end loop;
end p_get_sum;
/

create table mm_return_deliverer_his as select * from mm_return_deliverer where 1=2;

create or replace trigger change_return_deliverer before delete or update on mm_return_deliverer for each row
begin 
  insert into mm_return_deliverer_his(return_deliverer_id)values(:old.return_deliverer_id);
end;

select t.*,rowid from mm_return_deliverer_his t; 
delete mm_return_deliverer where return_deliverer_id='06RD2010010186';
